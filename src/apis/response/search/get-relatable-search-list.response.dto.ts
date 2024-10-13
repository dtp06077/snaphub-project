import ResponseDto from "../response.dto";

export default interface GetRelatableSearchListResponseDto extends ResponseDto {
    relatedSearchList: string[];
}