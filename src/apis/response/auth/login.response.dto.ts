import ResponseDto from "../response.dto";

export default interface LoginResponseDto extends ResponseDto {
    token: string;
    expirationTime: number;
}