import ResponseDto from "../response.dto";

export default interface UserInfoResponseDto extends ResponseDto {
    loginId: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string | null;
    address: string | null;
    addressDetail: string | null;
}