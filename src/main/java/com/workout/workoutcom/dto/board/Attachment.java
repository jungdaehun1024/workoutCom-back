package com.workout.workoutcom.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {
    private int attachmentId;
    private int boardId;
    private String attachmentName;
    private String attachmentPath;
    private String attachmentMIME;
}
