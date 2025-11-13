package online.rabko.monitor.configuration

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

private const val BEARER_PREFIX = "Bearer "

@Configuration
class SecurityConfiguration() {
    @Value("\${clients.api.key}")
    private lateinit var apiKey: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests { auth ->
            auth.requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/oas/**",
                "/error"
            ).permitAll()
            auth.anyRequest().authenticated()
        }
        .addFilterBefore(
            ApiKeyAuthFilter(apiKey),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .build()
}

class ApiKeyAuthFilter(private val expectedApiKey: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header?.startsWith(BEARER_PREFIX) ?: false) {
            val token = header.removePrefix(BEARER_PREFIX).trim()
            if (token == expectedApiKey) {
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(
                        "api-client",
                        null,
                        emptyList()
                    )
            }
        }
        filterChain.doFilter(request, response)
    }
}