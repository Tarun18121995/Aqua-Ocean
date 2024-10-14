package com.innovative.coder.aqua.Dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BaseResponseDto
{
    private UUID id;
    private String message;
}
