import UserService, {type User} from "./User.ts";

const defaultHeaders = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

function authHeader(user: User) {
    return {Authorization: `Bearer ${user.token}`}
}

export function buildParamsString(queryParams: any): string {
    let pairs: string[] = [];

    for (const key in queryParams) {
        const value = queryParams[key]
        if (value != null) {
            pairs.push(`${key}=${value}`);
        }
    }

    return "?" + pairs.join("&");
}

function myFetch(endpoint: string, headers: any, body: any, method: string, queryParams: any = null, withAuth = true): Promise<Response> {
    const user = UserService.getStoredUser();
    const auth = (withAuth && user != null) ? authHeader(user) : null;
    const requestBody = (body == null) ? null : JSON.stringify(body);
    const params = (queryParams != null) ? buildParamsString(queryParams) : "";
    const apiRoot = process.env.REACT_APP_API_ROOT!;
    const requestUrl = endpoint.startsWith("http") ?
        endpoint + params
        : apiRoot + endpoint + params;

    return fetch(requestUrl, {
        method: method,
        headers: {
            ...defaultHeaders,
            ...auth,
            ...headers
        },
        body: requestBody
    });
}

function apiGet(endpoint: string, headers: any = null, queryParams: any = null): Promise<Response> {
    return myFetch(endpoint, headers, null, "GET", queryParams);
}

function apiPost(endpoint: string, body: any, headers: any = null): Promise<Response> {
    return myFetch(endpoint, headers, body, "POST");
}

function apiPut(endpoint: string, body: any, headers: any = null): Promise<Response> {
    return myFetch(endpoint, headers, body, "PUT");
}

function apiDelete(endpoint: string, body: any = null, headers: any = null): Promise<Response> {
    return myFetch(endpoint, headers, body, "DELETE");
}


const api = {
    get: apiGet,
    post: apiPost,
    put: apiPut,
    delete: apiDelete,
};

export default api;