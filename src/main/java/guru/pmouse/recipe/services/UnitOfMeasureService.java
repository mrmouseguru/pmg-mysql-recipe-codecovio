package guru.pmouse.recipe.services;

import guru.pmouse.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> findAll();

}
