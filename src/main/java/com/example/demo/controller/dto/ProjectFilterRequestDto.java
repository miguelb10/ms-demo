package com.example.demo.controller.dto;

import com.example.demo.enums.ProjectSectorEnum;
import com.example.demo.enums.ProjectStageEnum;
import com.example.demo.enums.ProjectStatusEnum;
import com.example.demo.validator.annotation.ValidEachEnum;
import com.example.demo.validator.annotation.ValidEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ProjectFilterRequestDto {

    private List<@Size(min = 1, max = 10) String> areas;

    @ValidEachEnum(enumClass = ProjectStatusEnum.class, message = "msg.error.project.status.enum")
    private List<String> status;

    @ValidEachEnum(enumClass = ProjectSectorEnum.class, message = "msg.error.project.sector.enum")
    private List<String> sectors;

    @ValidEnum(enumClass = ProjectStageEnum.class, message = "msg.error.project.stage.enum")
    private String stage;
}
