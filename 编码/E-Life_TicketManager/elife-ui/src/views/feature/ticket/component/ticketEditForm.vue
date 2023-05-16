<template>
    <div>
        {{ ticket ? ticket.ticketId: "UNDEFINED" }}
        <el-form :model="ticket" >
            <el-form-item label="">

            </el-form-item>
            
        </el-form>

    </div>
</template>
<script>
export default{
    name:"TicketEditForm",
    props:{
        ticket: undefined,
    },
    methods:{

    }
}
</script>