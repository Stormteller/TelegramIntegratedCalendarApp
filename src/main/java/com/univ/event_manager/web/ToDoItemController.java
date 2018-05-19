package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.input.CreateToDoItemInput;
import com.univ.event_manager.data.dto.input.UpdateToDoItemInput;
import com.univ.event_manager.data.dto.output.ToDoItemResponse;
import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import com.univ.event_manager.data.exception.BadRequestException;
import com.univ.event_manager.service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todoitem")
public class ToDoItemController implements AuthenticatedController {
    private final ToDoItemService toDoItemService;

    @Autowired
    public ToDoItemController(ToDoItemService toDoItemService) {
        this.toDoItemService = toDoItemService;
    }

    @PostMapping
    private ResponseEntity<ToDoItemResponse> create(@RequestBody @Valid CreateToDoItemInput input,
                                                    BindingResult params,
                                                    Authentication authentication) {
        if (params.hasErrors()) {
            throw new BadRequestException(params.getFieldErrors().toString());
        }

        AuthorizedUserDetails authorizedUserDetails = authPrincipal(authentication);

        ToDoItemResponse toDoItem =
                toDoItemService.create(input.getToDoListId(), input.getText(), authorizedUserDetails.getId());

        return ResponseEntity.ok(toDoItem);
    }

    @PutMapping("/{id}")
    private ResponseEntity<ToDoItemResponse> update(@PathVariable("id") long id,
                                                    @RequestBody @Valid UpdateToDoItemInput input,
                                                    BindingResult params,
                                                    Authentication authentication) {
        if (params.hasErrors()) {
            throw new BadRequestException(params.getFieldErrors().toString());
        }

        ToDoItemResponse toDoItem = toDoItemService.update(id, input);

        return ResponseEntity.ok(toDoItem);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Boolean> delete(@PathVariable("id") long id,
                                           Authentication authentication) {
        toDoItemService.delete(id);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ToDoItemResponse> getById(@PathVariable("id") long id,
                                                     Authentication authentication) {

        ToDoItemResponse toDoItem = toDoItemService.getById(id);

        return ResponseEntity.ok(toDoItem);
    }
}
