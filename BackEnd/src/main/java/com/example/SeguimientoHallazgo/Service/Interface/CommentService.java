package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listAllCommentsByFindingAssigned(Long pFindingAssigned);
    Comment saveComment(Long pFindingAssigned, Comment pComment);
    boolean deleteComment(Long pCommentId);
}
