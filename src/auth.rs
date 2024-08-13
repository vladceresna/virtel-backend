use rocket::{get, post, form::Form, routes};
use rocket_auth::{Users, Error, Auth, Signup, Login};

#[get("/see-user/<id>")]
async fn see_user(id: i32, users: &State<Users>) -> String {
    let user = users.get_by_id(id).await.unwrap();
    format!("{}", json!(user))
}

#[post("/signup", data="<form>")]
async fn signup(form: Form<Signup>, auth: Auth<'_>) -> Result<&'static str, Error> {
    auth.signup(&form).await?;
    auth.login(&form.into());
    Ok("You signed up.")
}

#[post("/login", data="<form>")]
async fn login(form: rocket::serde::json::Json<Login>, auth: Auth<'_>) -> Result<&'static str, Error> {
    auth.login(&form).await?;
    Ok("You're logged in.")
}

#[get("/logout")]
fn logout(auth: Auth<'_>) {
    auth.logout();
}
#[tokio::main]
async fn main() -> Result<(), Error>{
    let users = Users::open_sqlite("mydb.db").await?;

    rocket::build()
        .mount("/", routes![signup, login, logout])
        .manage(users)
        .launch();
    Ok(())
}