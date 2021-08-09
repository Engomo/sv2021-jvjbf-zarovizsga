package org.training360.finalexam.players;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PlayerService {

    private ModelMapper modelMapper;

    private PlayerRepository repository;


    public List<PlayerDTO> getPlayers() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PlayerDTO.class))
                .collect(Collectors.toList());
    }

    public PlayerDTO createPlayer(CreatePlayerCommand command) {
        Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
        repository.save(player);
        return modelMapper.map(player, PlayerDTO.class);
    }

    public void deletePlayer(long id) {
        repository.deleteById(id);
    }
}
