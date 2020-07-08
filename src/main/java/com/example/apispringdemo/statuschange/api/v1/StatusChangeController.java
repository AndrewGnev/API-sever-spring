package com.example.apispringdemo.statuschange.api.v1;

import com.example.apispringdemo.statuschange.IStatusChangeService;
import com.example.apispringdemo.statuschange.StatusChangeEntity;
import com.example.apispringdemo.user.OnlineStatus;
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
            @RequestParam("status") OnlineStatus status,
            @RequestParam("time") long time
    ) {
        return service.getStatistics(status, time).parallelStream()
                .map(this::entityToDto).collect(Collectors.toList());
    };

    private StatusChangeDto entityToDto(StatusChangeEntity entity) {
        return new StatusChangeDto(entity.getId(), entity.getUser(), entity.getStatus(), entity.getTimestamp());
    }
}
