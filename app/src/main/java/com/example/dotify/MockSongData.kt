package com.example.dotify

class MockSongData {
    companion object MOCK_DATA {
        val MOCK_SONG_BATCH: String = """
        {
            "title": "Dotify",
            "numOfSongs": 2,
            "songs": [
               {
                    "id": "1588825540885InTheEnd_LinkinPark",
                    "title": "In The End",
                    "artist": "Linkin Park",
                    "durationMillis": 193790,
                    "smallImageURL": "https://picsum.photos/seed/InTheEnd/50",
                    "largeImageURL": "https://picsum.photos/seed/InTheEnd/256"
               },
               {
                     "id": "1588825540953MaskDefinitelyOn_Future",
                     "title": "Mask Definitely On",
                     "artist": "Future",
                     "durationMillis": 92949,
                     "smallImageURL": "https://picsum.photos/seed/MaskDefinitelyOn/50",
                     "largeImageURL": "https://picsum.photos/seed/MaskDefinitelyOn/256"
                }
            ]
        }
    """.trimIndent()

        val MOCK_SONG: String = """
        {
            "id": "1588825540885InTheEnd_LinkinPark",
            "title": "In The End",
            "artist": "Linkin Park",
            "durationMillis": 193790,
            "smallImageURL": "https://picsum.photos/seed/InTheEnd/50",
            "largeImageURL": "https://picsum.photos/seed/InTheEnd/256"
        }
    """.trimIndent()
    }
}