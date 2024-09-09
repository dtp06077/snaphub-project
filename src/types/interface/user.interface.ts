export default interface User {
    userId: number;
    loginId: string;
    email: string | null;
    name: string;
    profile: string | null;
    telNumber: string | null;
    address: string | null;
    roles: string[];
}