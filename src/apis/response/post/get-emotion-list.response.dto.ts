import { EmotionListItem } from "../../../types/interface";
import ResponseDto from "../response.dto";

export default interface GetEmotionListResponseDto extends ResponseDto {
    emotionList: EmotionListItem[]
}