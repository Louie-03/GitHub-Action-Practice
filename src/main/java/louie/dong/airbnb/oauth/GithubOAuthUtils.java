package louie.dong.airbnb.oauth;

public class GithubOAuthUtils {

	public static final String CALLBACK_URL = "http://louie-03.com/login/callback";
	public static final String CLIENT_ID = "92a2ecabd42ec456a9aa";
	public static final String CLIENT_SECRET = "62a429b5991a1bccfdfe4c0b863fcb11ee2a4cf5";
	public static final String LOCATION = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID +
		"&redirect_uri=" + CALLBACK_URL;
	public static final String ACCESS_TOKEN_API_URL = "https://github.com/login/oauth/access_token";
	public static final String USER_API_URL	= "https://api.github.com/user";
}
