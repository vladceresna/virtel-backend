#[macro_use] extern crate rocket;
use rocket::{get, post, form::Form, routes};
use rocket_auth::{Users, Error, Auth, Signup, Login};
use std::env;

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

#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}
#[get("/authed")]
fn authed(user: User) -> &'static str {
    "Yeah! You`re authenticated!"
}

#[tokio::main]
async fn main() -> Result<(), Error>{
    let url = env::var("POSTGRES_URL").expect("Env not setted");
    let users = Users::open_postgres(url.as_str()).await?;

    rocket::build()
        .mount("/", routes![signup, login, logout, index, authed])
        .manage(users)
        .launch();
    Ok(())
}