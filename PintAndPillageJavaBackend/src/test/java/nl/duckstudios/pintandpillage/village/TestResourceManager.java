package nl.duckstudios.pintandpillage.village;

import nl.duckstudios.pintandpillage.helper.ResourceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TestResourceManager {

    @Mock
    ResourceManager resourceManager;

    @BeforeEach
    void setup(){
        this.resourceManager = new ResourceManager();
    }

}
