CREATE INDEX IX_Orders_TicketCategoriesId_NumberOfTickets
ON Orders (TicketCategories_ID, NumberOfTickets)

CREATE INDEX IDX_Orders_TicketCategory_ID_TotalPrice
ON Orders (TicketCategories_ID, TotalPrice)

CREATE INDEX IX_Orders_UserId
ON Orders (User_ID)