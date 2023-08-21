export function getModifyModalTemplate(){
    return `
        <div class="modal" id="myModal">
            <div class="modal-content">
                <div class="modal-title">
                    <div class="modal-title-text">
                        Modify order details
                    </div>
                    <div class="close-button">
                        <span class="close">&times;</span>
                    </div>
                </div>
                <div class="modify-options">
                    <select id="selectTicketDescriptionInput"></select>
                    <div class="input-update">
                        <div class="down-button">
                            <button>-</button>
                        </div>
                        <input  id="inputNumberOfTickets" type="number"></input>
                        <div class="up-button">
                            <button>+</button>
                        </div>
                    </div>
                </div>
                <div class="update-option">
                    <div class="update-button">
                        <button>Update</button>
                    </div>
                </div>
                <div class="delete-option">
                    <div class="delete-button">
                        <button>Delete</button>
                    </div>
                </div>
            </div>
        </div>
    `
}