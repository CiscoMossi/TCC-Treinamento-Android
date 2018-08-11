package apps.com.br.tcc.dtos

data class MatchDetailDTO (
        var gameId: Int,
        var participantIdentities: List<ParticipantIdentityDTO>,
        var participants: List<ParticipantDTO>
){
}