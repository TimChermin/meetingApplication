package com.meetingapp.meetingapp.repository;

import com.meetingapp.meetingapp.repository.interfaces.GraphRepository;
import com.meetingapp.meetingapp.serviceimpl.AuthProvider;
import com.microsoft.aad.msal4j.*;
import com.microsoft.graph.concurrency.DefaultExecutors;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IUserCollectionPage;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphApi implements GraphRepository {
    private static AuthProvider authProvider;
    private static final Logger LOGGER = Logger.getLogger( GraphApi.class.getName() );

    public GraphApi() {
        IClientCredential credential = ClientCredentialFactory.createFromSecret("9YP0to43@QHeqnIr?:t:79l:@62yI/0W");
        IAuthenticationResult result = null;
        ConfidentialClientApplication cca;
        try {
             cca = ConfidentialClientApplication
                            .builder("bfa36f96-fa30-4f99-98fb-74e12a7175e9", credential)
                            .authority("https://login.microsoftonline.com/d26f6fd4-5d92-46a6-aab5-18876465d72c/")
                            .build();

                ClientCredentialParameters parameters =
                        ClientCredentialParameters
                                .builder(Collections.singleton("https://graph.microsoft.com/.default"))
                                .build();
                result = cca.acquireToken(parameters).join();
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING,  "Exception", e);
        }
        assert result != null;
        authProvider = new AuthProvider(result.accessToken());
    }

    @Override
    public List<User> getUsers() {
        IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).executors(new DefaultExecutors(null)).buildClient();
        IUserCollectionPage userCollectionPage = graphClient.users().buildRequest().select("displayName,mail,mailNickname,isResourceAccount").get();
        return userCollectionPage.getCurrentPage();
    }
}
