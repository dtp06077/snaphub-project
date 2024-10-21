import { PostListItem } from "../../../types/interface";
import ResponseDto from "../response.dto";

export default interface GetLatestPostListResponseDto extends ResponseDto {
    latestList: PostListItem[];
};
