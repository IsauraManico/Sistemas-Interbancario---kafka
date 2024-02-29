package ucan.edu.sb24.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ucan.edu.sb24.enums.ContaRole;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                          .requestMatchers( "/sb24/clientes/**").permitAll()

                                .requestMatchers( "/sb24/-status/**").permitAll()

                                .requestMatchers( "/sb24/saldohistorico/**").permitAll()//hasRole("CLIENTE")
                                .requestMatchers( "/history/**").permitAll()

                                .requestMatchers( "/sb24/transacoes/**").permitAll()//hasRole("CLIENTE")

                        .requestMatchers( "/sb24/contas/**").permitAll()//hasRole("CLIENTE")
                       // .anyRequest().authenticated()

                                // .requestMatchers( "/sb24/transferir/**").permitAll()
                                .requestMatchers( HttpMethod.POST,"/sb24/transferir/transferencia-intermediada").hasAnyAuthority(ContaRole.CLIENTE.name())
                                //.anyRequest().authenticated()
                                .requestMatchers( HttpMethod.POST,"/sb24/transferir/transferencia-interna").hasAnyAuthority(ContaRole.CLIENTE.name())
                                .anyRequest().authenticated()
                        ////////////////
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
