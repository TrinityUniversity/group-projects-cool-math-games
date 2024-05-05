package models

case class PictionaryPlayer(name:String, score:Int, isDrawing:Boolean, isHost:Boolean) {
    def addScore(points: Int): PictionaryPlayer = {
        this.copy(score = this.score + points)
    }
}