package org.creator.autovideocreator.controller;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.project.CreateProjectDto;
import org.creator.autovideocreator.dto.project.ResponseProjectDto;
import org.creator.autovideocreator.service.data.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseProjectDto saveProject(@RequestBody CreateProjectDto createProjectDto) {
        return projectService.save(createProjectDto);
    }

    @GetMapping("/{telegramId}")
    public List<ResponseProjectDto> findByTelegramId(@PathVariable String telegramId) {
        return projectService.findByTelegramId(telegramId);
    }
}
