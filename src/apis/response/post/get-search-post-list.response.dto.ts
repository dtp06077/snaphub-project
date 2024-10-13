import { PostListItem } from "../../../types/interface";
import ResponseDto from "../response.dto";

export default interface GetSearchPostListResponseDto extends ResponseDto {
    searchList: PostListItem[];
}