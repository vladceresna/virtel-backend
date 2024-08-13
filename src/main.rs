#[macro_use] extern crate rocket;
mod auth;

#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}
#[get("/authed")]
fn index() -> &'static str {
    "Yeah! You`re authenticated!"
}

#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![index])
}