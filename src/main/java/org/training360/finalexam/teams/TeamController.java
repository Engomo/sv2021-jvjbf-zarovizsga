package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private TeamService service;

    @GetMapping
    public List<TeamDTO> getTeams() {
        return service.getTeams();
    }

    @PostMapping
    public TeamDTO createTeam(@Valid @RequestBody CreateTeamCommand command) {
        return service.createTeam(command);
    }

    @PostMapping("/{id}/players")
    public TeamDTO addPlayer(@PathVariable("id") long id, @Valid @RequestBody CreatePlayerCommand command) {
        return service.addPlayer(id, command);
    }

    @PutMapping("/{id}/players")
    public TeamDTO addExistingPlayer(@PathVariable("id") long id, @RequestBody UpdateWithExistingPlayerCommand command) {
        return service.addExistingPlayer(id, command);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("teams/not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }
}
