package com.gsa.usuario.business;

import com.gsa.usuario.business.converter.UsuarioConverter;
import com.gsa.usuario.business.dto.UsuarioDTO;
import com.gsa.usuario.exceptions.ConflictExceptions;
import com.gsa.usuario.exceptions.ResorceNotFoundException;
import com.gsa.usuario.infrastructure.entity.usuario;
import com.gsa.usuario.infrastructure.repository.UsuarioRepository;
import com.gsa.usuario.infrastructure.security.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class usuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictExceptions("email existente " + email);
            }
        }catch (ConflictExceptions e){
            throw new ConflictExceptions("email ja existente " + e.getCause());
        }
    }


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResorceNotFoundException("email não encontrado " + email));
    }

    public void  deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token,UsuarioDTO dto){

        //aqui buscamos o email do usuario atraves do token(tirar a obrigatoriedade do email)
        String email = jwtUtil.extrairEmailtoken(token.substring(7));

        //busca os dados do ususario no banco de dados
        usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResorceNotFoundException("Email não localizado"));

        //mesclo os dados que recebemos na requisição DTO com os dados do banco de dados
        usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //salvou os dados do usuario convertido e depois pegou o retorno e converteu para usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
