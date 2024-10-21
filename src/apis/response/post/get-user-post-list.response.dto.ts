import { PostListItem } from "../../../types/interface";
import ResponseDto from "../response.dto";

export default interface GetUserPostListResponseDto extends ResponseDto {
    userPostList: PostListItem[];
}