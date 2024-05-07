package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Scores.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Scores
   *  @param userid Database column userid SqlType(int4)
   *  @param gameid Database column gameid SqlType(int4)
   *  @param score Database column score SqlType(varchar) */
  case class ScoresRow(userid: Int, gameid: Int, score: String)
  /** GetResult implicit for fetching ScoresRow objects using plain SQL queries */
  implicit def GetResultScoresRow(implicit e0: GR[Int], e1: GR[String]): GR[ScoresRow] = GR{
    prs => import prs._
    ScoresRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table scores. Objects of this class serve as prototypes for rows in queries. */
  class Scores(_tableTag: Tag) extends profile.api.Table[ScoresRow](_tableTag, "scores") {
    def * = (userid, gameid, score).<>(ScoresRow.tupled, ScoresRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userid), Rep.Some(gameid), Rep.Some(score))).shaped.<>({r=>import r._; _1.map(_=> ScoresRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userid SqlType(int4) */
    val userid: Rep[Int] = column[Int]("userid")
    /** Database column gameid SqlType(int4) */
    val gameid: Rep[Int] = column[Int]("gameid")
    /** Database column score SqlType(varchar) */
    val score: Rep[String] = column[String]("score")

    /** Foreign key referencing Users (database name scores_userid_fkey) */
    lazy val usersFk = foreignKey("scores_userid_fkey", userid, Users)(r => r.userid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Scores */
  lazy val Scores = new TableQuery(tag => new Scores(tag))

  /** Entity class storing rows of table Users
   *  @param userid Database column userid SqlType(int4), PrimaryKey
   *  @param username Database column username SqlType(varchar)
   *  @param email Database column email SqlType(varchar)
   *  @param password Database column password SqlType(varchar), Default(None) */
  case class UsersRow(userid: Int, username: String, email: String, password: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (userid, username, email, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userid), Rep.Some(username), Rep.Some(email), password)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userid SqlType(int4), PrimaryKey */
    val userid: Rep[Int] = column[Int]("userid", O.PrimaryKey)
    /** Database column username SqlType(varchar) */
    val username: Rep[String] = column[String]("username")
    /** Database column email SqlType(varchar) */
    val email: Rep[String] = column[String]("email")
    /** Database column password SqlType(varchar), Default(None) */
    val password: Rep[Option[String]] = column[Option[String]]("password", O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
