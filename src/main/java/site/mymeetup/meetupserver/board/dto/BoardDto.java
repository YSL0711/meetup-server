package site.mymeetup.meetupserver.board.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mymeetup.meetupserver.board.entity.Board;
import site.mymeetup.meetupserver.crew.entity.Crew;
import site.mymeetup.meetupserver.crew.entity.CrewMember;

public class BoardDto {

    @Getter
    @NoArgsConstructor
    public static class BoardSaveReqDto {
        @NotNull(message = "제목은 필수 입력사항입니다.")
        @Size(max = 20, message = "제목은 20자 이하여야 합니다.")
        private String title;

        @NotNull(message = "본문은 필수 입력사항입니다.")
        @Size(max = 3000, message = "본문은 3000자 이하여야 합니다.")
        private String content;

        @NotNull(message = "카테고리는 필수 선택사항입니다.")
        private String category;

        private Long crewId;
        private Long crewMemberId;

        public Board toEntity(Crew crew, CrewMember crewMember) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .category(category)
                    .crew(crew)
                    .crewMember(crewMember)
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BoardSaveRespDto {
        private Long BoardId;

        @Builder
        public BoardSaveRespDto(Board board) {
            this.BoardId = board.getBoardId();
        }
    }

}
