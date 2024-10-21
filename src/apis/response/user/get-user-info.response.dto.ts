import ResponseDto from "../response.dto";

export default interface GetUserInfoResponseDto extends ResponseDto {
    userId: number;
    loginId: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string;
    address: string | null;
    roles: string[];
}