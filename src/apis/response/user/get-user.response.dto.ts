import ResponseDto from "../response.dto";
import { UserInfo } from "../../../types/interface";

export default interface GetUserResponseDto extends ResponseDto,UserInfo {
    
}