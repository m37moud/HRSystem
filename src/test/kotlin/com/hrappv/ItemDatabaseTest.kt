class ItemDatabaseTest {

    private val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }

    private val queries = Database(inMemorySqlDriver).itemInCartEntityQueries

    @Test
    fun smokeTest() {
        val emptyItems: List = queries.selectAll().executeAsList()
        assertEquals(emptyItems.size, 0)

        queries.insertOrReplace(
            label = "Pineapple",
            image = "https://localhost/pineapple.png",
            quantity = 5,
            link = null
        )

        val items: List = queries.selectAll().executeAsList()
        assertEquals(items.size, 1)

        val pineappleItem = queries.selectByLabel("Pineapple").executeAsOneOrNull()
        assertEquals(pineappleItem?.image, "https://localhost/pineapple.png")
        assertEquals(pineappleItem?.quantity?.toInt(), 5)
    }
}
