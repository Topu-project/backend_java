package jp.co.topucomunity.backend_java.users.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRegistrationFacade {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public String getIssuerUri() {
        return getClientRegistration().getProviderDetails().getIssuerUri();
    }

    public ClientRegistration getClientRegistration() {
        return clientRegistrationRepository.findByRegistrationId("google");
    }
}
