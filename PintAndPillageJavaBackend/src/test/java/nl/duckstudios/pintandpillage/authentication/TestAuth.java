package nl.duckstudios.pintandpillage.authentication;

import nl.duckstudios.pintandpillage.dao.UserDAO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Authentication")
public class TestAuth {

    @Mock
    private UserDAO userDao;

//    private setupAuthStub(){
//    }
    @Test
    public void Should_ReturnTokenWithJWT_When_UserIsRegisterd() {


    }
}
