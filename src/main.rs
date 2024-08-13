#[macro_use] extern crate rocket;
mod auth;

#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}
#[get("/authed")]
fn authed(user: User) -> &'static str {
    "Yeah! You`re authenticated!"
}

#[tokio::main]
#[launch]
async fn rocket() -> Result<(), Error>{
    let users = Users::open_sqlite("mydb.db").await?;
    rocket::build()
    .mount("/", routes![index, signup, login, logout])
    .manage(users)
    .launch();
    Ok(())
}