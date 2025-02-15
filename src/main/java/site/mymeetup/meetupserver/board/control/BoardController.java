package site.mymeetup.meetupserver.board.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static site.mymeetup.meetupserver.board.dto.BoardDto.BoardSaveRespDto;
import static site.mymeetup.meetupserver.board.dto.BoardDto.BoardRespDto;
import static site.mymeetup.meetupserver.board.dto.BoardDto.BoardSaveReqDto;
import static site.mymeetup.meetupserver.board.dto.CommentDto.CommentSaveReqDto;
import static site.mymeetup.meetupserver.board.dto.CommentDto.CommentSaveRespDto;

import static site.mymeetup.meetupserver.board.dto.CommentDto.CommentRespDto;
import site.mymeetup.meetupserver.board.service.BoardService;
import site.mymeetup.meetupserver.member.dto.CustomUserDetails;
import site.mymeetup.meetupserver.response.ApiResponse;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/crews/{crewId}/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 등록
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<BoardSaveRespDto> createBoard(@PathVariable Long crewId,
                                                     @RequestBody @Valid BoardSaveReqDto boardSaveReqDto,
                                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ApiResponse.success(boardService.createBoard(crewId, boardSaveReqDto, customUserDetails));
    }

    // 게시글 수정
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{boardId}")
    public ApiResponse<BoardSaveRespDto> updateBoard(@PathVariable Long crewId,
                                                     @PathVariable Long boardId,
                                                     @RequestBody @Valid BoardSaveReqDto boardSaveReqDto,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(boardService.updateBoard(crewId, boardId, boardSaveReqDto, userDetails));
    }

    // 게시글 고정
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{boardId}/pin")
    public ApiResponse<BoardSaveRespDto> updateBoardStatus(@PathVariable Long crewId,
                                            @PathVariable Long boardId,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(boardService.updateBoardStatus(crewId, boardId, userDetails));
    }

    // 게시글 이미지 ajax 처리
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/images")
    public ApiResponse< List<String>> uploadImage(@RequestPart @Valid MultipartFile[] images) {
        return ApiResponse.success(boardService.uploadImage(images));
    }

    // 게시글 목록 전체 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponse<List<BoardRespDto>> getBoardByCrewId(@PathVariable Long crewId,
                                                            @RequestParam(required = false) String category,
                                                            @RequestParam(defaultValue = "0") int page) {
        return ApiResponse.success(boardService.getBoardByCrewId(crewId, category, page));
    }

    // 특정 게시글 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}")
    public ApiResponse<BoardRespDto> getBoardOndByBoardId(@PathVariable Long crewId,
                                                          @PathVariable Long boardId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(boardService.getBoardByBoardId(crewId, boardId, userDetails));
    }

    // 게시글 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{boardId}")
    public ApiResponse<?> deleteBoard(@PathVariable Long crewId,
                                      @PathVariable Long boardId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardService.deleteBoard(crewId, boardId, userDetails);
        return ApiResponse.success(null);
    }

    // 댓글 등록
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{boardId}/comments")
    public ApiResponse<CommentSaveRespDto> createComment(@PathVariable Long crewId,
                                                         @PathVariable Long boardId,
                                                         @RequestBody CommentSaveReqDto commentSaveReqDto,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(boardService.createComment(crewId, boardId, commentSaveReqDto, userDetails));
    }

    // 댓글 수정
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{boardId}/comments/{commentId}")
    public ApiResponse<CommentSaveRespDto> updateComment(@PathVariable Long crewId,
                                                         @PathVariable Long boardId,
                                                         @PathVariable Long commentId,
                                                         @RequestBody CommentSaveReqDto commentSaveReqDto,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(boardService.updateComment(crewId, boardId, commentId, commentSaveReqDto, userDetails));
    }

    // 댓글 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ApiResponse<?> deleteComment(@PathVariable Long crewId,
                                        @PathVariable Long boardId,
                                        @PathVariable Long commentId,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardService.deleteComment(crewId, boardId, commentId, userDetails);
        return ApiResponse.success(null);
    }

    // 댓글 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}/comments")
    public ApiResponse<List<CommentRespDto>> getCommentByBoardId(@PathVariable Long crewId,
                                                                 @PathVariable Long boardId,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @RequestParam(defaultValue = "0") int page) {
        return ApiResponse.success(boardService.getCommentByBoardId(crewId, boardId, userDetails, page));
    }
}
