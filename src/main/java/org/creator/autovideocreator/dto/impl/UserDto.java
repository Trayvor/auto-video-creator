package org.creator.autovideocreator.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.creator.autovideocreator.dto.UsualDto;
import org.creator.autovideocreator.model.Project;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto implements UsualDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String telegram_id;
    @NotBlank
    private List<Project> projects = new ArrayList<>();
}
