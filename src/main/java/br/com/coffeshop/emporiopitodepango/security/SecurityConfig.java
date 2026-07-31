package br.com.coffeshop.emporiopitodepango.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // pagina de login e recursos estaticos - sempre livres
                .requestMatchers("/admin/login", "/css/**", "/js/**", "/img/**").permitAll()
                // usuarios do painel: só quem é GERENTE mexe
                .requestMatchers("/admin/usuarios/**").hasRole("GERENTE")
                // caixa: GERENTE e FINANCEIRO
                .requestMatchers("/admin/caixa/**").hasAnyRole("GERENTE", "FINANCEIRO")
                // excluir pedido (venda ja registrada) e sensivel - so GERENTE e FINANCEIRO;
                // consultar o historico continua liberado pros 3 perfis via regra geral abaixo
                .requestMatchers("/admin/pedidos/excluir/**").hasAnyRole("GERENTE", "FINANCEIRO")
                // resto do painel (dashboard, fornecedores, clientes, pedidos): qualquer perfil logado
                .requestMatchers("/admin/**").hasAnyRole("GERENTE", "FINANCEIRO", "ATENDENTE")
                // o catalogo de produtos (loja) continua publico para leitura,
                // mas cadastrar/editar/excluir produto exige estar logado
                .requestMatchers("/produtos/novo", "/produtos/salvar", "/produtos/excluir/**")
                    .hasAnyRole("GERENTE", "FINANCEIRO", "ATENDENTE")
                // loja publica (home, catalogo, carrinho, sobre, contato) - livre
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin", true)
                .failureUrl("/admin/login?erro")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
