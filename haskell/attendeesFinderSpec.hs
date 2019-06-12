import Test.Hspec

data Attendee = Attendee {
     firstName :: String
} deriving (Show, Eq)

main = hspec $ do
    let attendees = [Attendee { firstName = "Marc" }, 
                     Attendee { firstName = "Christelle" },
                     Attendee { firstName = "Christophe" }]

    describe "The attendees finder should return" $ do
        it "an empty result when no attendee first name matches the query string" $ do
            findByInfixOfFirstName "Paul" attendees `shouldBe` []

        it "one result when only one attendee first name matches the query string" $ do
            findByInfixOfFirstName "Marc" attendees `shouldBe` [Attendee {firstName = "Marc"}]

        it "all the results when many attendees first names match the query string" $ do
            findByInfixOfFirstName "hri" attendees `shouldBe` [Attendee {firstName = "Christelle"}, Attendee {firstName = "Christophe"}]
