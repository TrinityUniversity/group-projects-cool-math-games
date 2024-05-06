package models

case class User (userId: Int, username: String) {
    
}

case class UserExtras(email: String, scores: Seq[Score]) {

}

case class Score(game: String, score: Int){

}