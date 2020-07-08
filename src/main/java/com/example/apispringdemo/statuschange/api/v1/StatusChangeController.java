package com.example.apispringdemo.statuschange.api.v1;

import com.example.apispringdemo.statuschange.IStatusChangeService;
import com.example.apispringdemo.statuschange.StatusChangeEntity;
import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import com.example.apispringdemo.user.api.v1.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/statusChange")
public class StatusChangeController {

    private final IStatusChangeService service;

    public StatusChangeController(IStatusChangeService service) { this.service = service; }

    @GetMapping
    List<StatusChangeDto> get(
            @RequestParam(name = "status", required = false) OnlineStatus status,
            @RequestParam(name = "timestamp", required = false) Long timestamp
    ) {
        return service.getStatistics(status, timestamp).parallelStream()
                .map(StatusChangeController::entityToDto)
                .collect(Collectors.toList());
    };

    private static StatusChangeDto entityToDto(StatusChangeEntity entity) {
        return new StatusChangeDto(entity.getId(), entityToDto(entity.getUser()), entity.getTimestamp());
    }

    private static UserDto entityToDto(UserEntity entity) {
        return new UserDto(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getStatus());
    }
}
