package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.domain.Cidade;
import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.domain.Endereco;
import com.github.pedrobacchini.springionicdomain.dto.ClienteDTO;
import com.github.pedrobacchini.springionicdomain.dto.ClienteNewDTO;
import com.github.pedrobacchini.springionicdomain.enums.Perfil;
import com.github.pedrobacchini.springionicdomain.enums.TipoCliente;
import com.github.pedrobacchini.springionicdomain.repository.ClienteRepository;
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
public class ClienteService {

    private final LocaleMessageSource localeMessageSource;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationProperties applicationProperties;

    private final S3Service s3Service;
    private final ImageService imageService;

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {

        Optional<ClientUserDetails> authenticated = UserService.authenticated();

        if (!authenticated.isPresent() || !authenticated.get().hasRole(Perfil.ADMIN) &&
                !id.equals(authenticated.get().getId())) {
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));
        }
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        localeMessageSource.getMessage("object-not-found", "id", id, Cliente.class.getName())));
    }

    public Cliente findByEmail(String email) {
        Optional<ClientUserDetails> authenticated = UserService.authenticated();

        if (!authenticated.isPresent() || !authenticated.get().hasRole(Perfil.ADMIN) &&
                !email.equals(authenticated.get().getUsername())) {
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));
        }
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException(
                        localeMessageSource.getMessage("object-not-found", "email", email, Cliente.class.getName())));
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        //Para ter certeza que e um atualização e nao uma inserção
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente clientPersisted = find(cliente.getId());
        updateData(clientPersisted, cliente);
        return clienteRepository.save(clientPersisted);
    }

    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(localeMessageSource.getMessage("not-delete-customer-with-orders"));
        }
    }

    public List<Cliente> findAll() { return clienteRepository.findAll(); }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO) {
        return new Cliente(
                clienteDTO.getId(),
                clienteDTO.getNome(),
                clienteDTO.getEmail(),
                null,
                null,
                null);
    }

    public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = new Cliente(
                null,
                clienteNewDTO.getNome(),
                clienteNewDTO.getEmail(),
                clienteNewDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(clienteNewDTO.getTipo()),
                bCryptPasswordEncoder.encode(clienteNewDTO.getSenha()));

        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(
                null,
                clienteNewDTO.getLogradouro(),
                clienteNewDTO.getNumero(),
                clienteNewDTO.getComplemento(),
                clienteNewDTO.getBairro(),
                clienteNewDTO.getCep(),
                cliente,
                cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if (clienteNewDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        }
        return cliente;
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

    private void updateData(Cliente clientPersisted, Cliente cliente) {
        clientPersisted.setNome(cliente.getNome());
        clientPersisted.setEmail(cliente.getEmail());
    }
}
