package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.input.CreateEventInput;
import com.univ.event_manager.data.dto.input.CreateToDoListInput;
import com.univ.event_manager.data.dto.input.ToDoListFilterInput;
import com.univ.event_manager.data.dto.output.ToDoListResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.entity.ToDoList;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todolist")
public class ToDoListController implements AuthenticatedController {

    private final ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping
    public ResponseEntity<ToDoListResponse> create(@RequestBody @Valid CreateToDoListInput input,
                                                   BindingResult params,
                                                   Authentication authentication) {
        if(params.hasErrors()) {
            throw new BadRequestException(params.getFieldErrors().toString());
        }

        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        ToDoListResponse toDoListResponse = toDoListService.create(input, authorizedUserDetails.getId());

        return ResponseEntity.ok(toDoListResponse);
    }

    @GetMapping
    public ResponseEntity<List<ToDoListResponse>> create(ToDoListFilterInput input,
                                                         Authentication authentication) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        List<ToDoListResponse> toDoLists = toDoListService.getByFilter(input, authorizedUserDetails.getId());

        return ResponseEntity.ok(toDoLists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoListResponse> getById(@PathVariable("id") long id,
                                                    Authentication authentication) {
        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        ToDoListResponse toDoList = toDoListService.getById(id, authorizedUserDetails.getId());

        return ResponseEntity.ok(toDoList);
    }
}
