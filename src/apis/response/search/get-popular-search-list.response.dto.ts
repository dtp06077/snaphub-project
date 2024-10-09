import ResponseDto from "../response.dto";

export default interface GetPopularSearchListResponseDto extends ResponseDto {
    popularSearchList: string[];
}