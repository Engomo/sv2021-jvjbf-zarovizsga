package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.Player;
import org.training360.finalexam.players.PlayerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeamService {

    private ModelMapper modelMapper;

    private TeamRepository repository;

    private PlayerRepository playerRepository;

    public List<TeamDTO> getTeams() {
        return repository.findAll().stream()
                .map(t -> modelMapper.map(t, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO createTeam(CreateTeamCommand command) {
        Team team = new Team(command.getName());
        repository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addPlayer(long id, CreatePlayerCommand command) {
        Team team = repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("team not found."));
        Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
        team.addPlayer(player);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addExistingPlayer(long id, UpdateWithExistingPlayerCommand command) {
        Team team = repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("team not found."));
        Player player = playerRepository.findById(command.getId())
                .orElseThrow(()-> new IllegalArgumentException("player not found"));
        if (player.getTeam() == null && numbersOfSamePosition(team, player) < 2) {
            team.addPlayer(player);
        }
        return modelMapper.map(team, TeamDTO.class);
    }

    private long numbersOfSamePosition(Team team, Player player) {
        return team.getPlayers().stream()
                .filter(p -> player.getPosition().equals(p.getPosition()))
                .count();
    }
}
