package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.domain.Cidade;
import com.github.pedrobacchini.springionicdomain.domain.Client;
import com.github.pedrobacchini.springionicdomain.domain.Endereco;
import com.github.pedrobacchini.springionicdomain.dto.ClientDTO;
import com.github.pedrobacchini.springionicdomain.dto.ClientNewDTO;
import com.github.pedrobacchini.springionicdomain.enums.Role;
import com.github.pedrobacchini.springionicdomain.enums.ClientType;
import com.github.pedrobacchini.springionicdomain.repository.ClientRepository;
import com.github.pedrobacchini.springionicdomain.repository.EnderecoRepository;
import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import com.github.pedrobacchini.springionicdomain.service.exception.AuthorizationException;
import com.github.pedrobacchini.springionicdomain.service.exception.DataIntegrityException;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final LocaleMessageSource localeMessageSource;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationProperties applicationProperties;

    private final S3Service s3Service;
    private final ImageService imageService;

    private final ClientRepository clientRepository;
    private final EnderecoRepository enderecoRepository;

    public Client find(Integer id) {

        Optional<ClientUserDetails> authenticated = UserService.authenticated();

        if (!authenticated.isPresent() || !authenticated.get().hasRole(Role.ADMIN) &&
                !id.equals(authenticated.get().getId())) {
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));
        }
        return clientRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        localeMessageSource.getMessage("object-not-found", "id", id, Client.class.getName())));
    }

    public Client findByEmail(String email) {
        Optional<ClientUserDetails> authenticated = UserService.authenticated();

        if (!authenticated.isPresent() || !authenticated.get().hasRole(Role.ADMIN) &&
                !email.equals(authenticated.get().getUsername())) {
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));
        }
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException(
                        localeMessageSource.getMessage("object-not-found", "email", email, Client.class.getName())));
    }

    @Transactional
    public Client insert(Client client) {
        //Para ter certeza que e um atualização e nao uma inserção
        client.setId(null);
        client = clientRepository.save(client);
        enderecoRepository.saveAll(client.getEnderecos());
        return client;
    }

    public Client update(Client client) {
        Client clientPersisted = find(client.getId());
        updateData(clientPersisted, client);
        return clientRepository.save(clientPersisted);
    }

    public void delete(Integer id) {
        find(id);
        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(localeMessageSource.getMessage("not-delete-customer-with-orders"));
        }
    }

    public List<Client> findAll() { return clientRepository.findAll(); }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clientRepository.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO clientDTO) {
        return new Client(
                clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getEmail(),
                null,
                null,
                null);
    }

    public Client fromDTO(ClientNewDTO clientNewDTO) {
        Client client = new Client(
                null,
                clientNewDTO.getName(),
                clientNewDTO.getEmail(),
                clientNewDTO.getCpfOrCnpj(),
                ClientType.toEnum(clientNewDTO.getType()),
                bCryptPasswordEncoder.encode(clientNewDTO.getPassword()));

        Cidade cidade = new Cidade(clientNewDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(
                null,
                clientNewDTO.getLogradouro(),
                clientNewDTO.getNumero(),
                clientNewDTO.getComplemento(),
                clientNewDTO.getBairro(),
                clientNewDTO.getCep(),
                client,
                cidade);
        client.getEnderecos().add(endereco);
        client.getPhones().add(clientNewDTO.getTelefone1());
        if (clientNewDTO.getTelefone2() != null) {
            client.getPhones().add(clientNewDTO.getTelefone2());
        }
        if (clientNewDTO.getTelefone3() != null) {
            client.getPhones().add(clientNewDTO.getTelefone3());
        }
        return client;
    }

    public URI uploadProfilePicture(MultipartFile multipartFile, ClientUserDetails clientUserDetails) {
        if (clientUserDetails == null)
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));

        BufferedImage bufferedImage = imageService.getJpgImageFromFile(multipartFile);
        bufferedImage = imageService.cropSquare(bufferedImage);
        bufferedImage = imageService.resize(bufferedImage, applicationProperties.getImage().getProfile().getSize());
        String fileName = applicationProperties.getImage().getProfile().getPrefix() + clientUserDetails.getId() + ".jpg";
        return s3Service.uploadFile(imageService.getInputStream(bufferedImage, "jpg"), fileName, multipartFile.getContentType());
    }

    private void updateData(Client clientPersisted, Client client) {
        clientPersisted.setName(client.getName());
        clientPersisted.setEmail(client.getEmail());
    }

    public Client getAuthenticationClient() {
        Optional<ClientUserDetails> authenticated = UserService.authenticated();
        if (!authenticated.isPresent())
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));
        else
            return find(authenticated.get().getId());
    }
}
