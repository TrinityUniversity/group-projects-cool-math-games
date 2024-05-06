package models

object CodeGen extends App {
    slick.codegen.SourceCodeGenerator.run(
        "slick.jdbc.PostgresProfile",
        "org.postgresql.Driver",
        "jdbc:postgresql://localhost/coolmathgames?user=bennettmach&password=postgres",
        "/Users/bennettmach/Documents/Web Apps/group-projects-cool-math-games/server/app/",
        "models", None, None, true, false
    )
}