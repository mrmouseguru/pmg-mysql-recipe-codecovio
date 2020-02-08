package guru.pmouse.recipe.services;

import guru.pmouse.recipe.commands.UnitOfMeasureCommand;
import guru.pmouse.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.pmouse.recipe.domain.UnitOfMeasure;
import guru.pmouse.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by PMouse Guru  on 01/02/2020
 */
@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @InjectMocks
    UnitOfMeasureServiceImpl unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void findAllUom() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);

        unitOfMeasures.add(unitOfMeasure1);unitOfMeasures.add(unitOfMeasure2);
        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAll();

        //then
        assertNotNull(unitOfMeasureCommands);
        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();

    }
}