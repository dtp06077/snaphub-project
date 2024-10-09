import { PostListItem } from "../../../types/interface";
import ResponseDto from "../response.dto";

export default interface GetTop3PostListResponseDto extends ResponseDto {
    top3List: PostListItem[];
}